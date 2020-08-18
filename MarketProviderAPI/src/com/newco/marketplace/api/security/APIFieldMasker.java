package com.newco.marketplace.api.security;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.api.annotation.MaskedValue;
import com.newco.marketplace.api.annotation.OptionalParam;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.utils.utility.Tree;
import com.newco.marketplace.api.utils.utility.TreeNode;
import com.newco.marketplace.business.iBusiness.api.mobile.IAPISecurity;
import com.newco.marketplace.utils.SimpleCache;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 *
 * This class is used by BaseService to perform masking for fields in the API response. Rules for masking
 * can be set in api_field_mask in Supplier database. This class uses reflection to achieve the task.
 *
 * I know people think that Reflection is dead slow. Hence I did some bench marking to prove that its costing too much.
 * On a mediocre computer it takes around 30ms (50 ms worst case) to mask 100 fields, which is equivalent to
 * one additional DB hit.
 *
 * Example for Setting Rules : Lets say you want to mask 'name' field in following XML.
 * 	<providerResults>
 * 		<provider resourceId="23513" distance="23.62">
 *   		<name>James s</name>
 *   		<memberSince>2008-03-12</memberSince>
 * 		</provider>
 * 	</providerResults>
 *
 * Rules in the DB should be : providerResults.provider.name
 *
 * @author Shekhar Nirkhe
 *
 */
public class APIFieldMasker {

	private IAPISecurity apiSecurity;
	Map<String, Tree<String>> consumerMap = new HashMap<String, Tree<String>>();
	private static Date MASKED_DATE = new Date(1);
	//private static final String DATE_TIME = "1970-01-01T01:00:00";
	public APIFieldMasker() {

	}

	/**
	 * This method read rules from the DB, create a Tree object and put it in the cache.
	 * @param consumerKey
	 * @return
	 */
	public Tree<String> readRules(String consumerKey) {
		List<String> list = apiSecurity.getAPISecurityFieldRules(consumerKey);
		TreeNode<String> root = new TreeNode<String>("/");
		Tree<String> cTree =  new Tree<String>(root);

		for (String rule:list) {
			if (StringUtils.isNotBlank(rule)) {
				addRule(rule, cTree);
			}
		}
		return cTree;
	}

	@SuppressWarnings("unchecked")
	public void checkRules(String consumerKey, IAPIResponse obj) {
		String key = "/API/APIFieldMasker/" + consumerKey;
		Tree<String> localtree = (Tree<String>)SimpleCache.getInstance().get(key);
		if (localtree == null) {
			localtree = readRules(consumerKey);
			SimpleCache.getInstance().put(key, localtree, SimpleCache.TEN_MINUTES);
		}

		if (localtree.getRootElement().isLeaf()) { //No rules, empty tree.
			return;
		}
		mask(obj, localtree);
	}



	/**
	 * This method parse DB output, convert them into Tree nodes and add them to the Tree.
	 * @param str
	 * @param myTree
	 */
	private void addRule(String str, Tree<String> tree) {
		TreeNode<String> node = tree.getRootElement();
		if (str != null) {
			str = str.trim();
			String arr[] = str.split("\\.");
			for (String val:arr) {
				TreeNode<String> child = new TreeNode<String>(val);
				TreeNode<String> newNode = node.search(child);
				if (newNode == null) {
					node.addChild(child);
				} else {
					child = newNode;
				}
				node = child;
			}
		}
	}


	/**
	 * The method scan the passed response object and search for any matched rule. If a field is matched
	 * against a rule it will be masked(or removed if it is annotated with '@OptionsParam').
	 *
	 * @param obj
	 * @param tree
	 */
	private void mask(IAPIResponse obj, Tree<String> tree){
		if (obj == null || tree == null)
			return;

		try {
		com.thoughtworks.xstream.annotations.XStreamAlias ann =
			obj.getClass().getAnnotation(com.thoughtworks.xstream.annotations.XStreamAlias.class);

		if (ann == null)
			return;

		String name = ann.value();
		TreeNode<String> node = tree.getRootElement().search(new TreeNode<String>(name));
		if (node != null) {
			loopTree(node, obj);
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * It find outs the object of the requested child.
	 *
	 * @param parent
	 * @param childName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */

	private Object getChildObj(Object parent, String childName) throws IllegalArgumentException, IllegalAccessException {
		if (parent == null || childName == null)
			return null;

		Class<?> c = parent.getClass();
		Field []fields = c.getDeclaredFields();

		for (Field field: fields) {
			field.setAccessible(true);
			XStreamAlias fieldAnn = field.getAnnotation(XStreamAlias.class);
			if (fieldAnn != null) {
				if (fieldAnn.value().equalsIgnoreCase(childName)) {
					return field.get(parent);
				}
			} else {
				XStreamImplicit fieldAnnx = field.getAnnotation(XStreamImplicit.class);
				if (fieldAnnx != null) {
					if (fieldAnnx.itemFieldName().equals(childName)) {
						return field.get(parent);
					}
				}
			}
		}
		return null;
	}


	private void loopTree(TreeNode<String> node, Object obj) throws IllegalArgumentException, IllegalAccessException  {
		if (obj == null || node == null)
			return;
		for (TreeNode<String> n: node.getChildren()) {
			if (n.isLeaf()) {
				//System.out.println(n.getData() + " is a leaf");
				loopField(n, obj);
				continue;
			}

			Object child = getChildObj(obj, n.getData());
			if (child != null) {
			  //System.out.println(n.getData() + ":" + child.getClass());
			  loopTree(n, child);
			}
		}

		if (obj instanceof List) {
			for (Object oo: (List)obj) {
				 loopTree(node, oo);
			}
		}
	}

	/**
	 * It is a recursive method to loop tree and find out the object to be masked.
	 * WARNING : It is a bug prone method. Please do not play with it unless you are
	 * clear about its functionality.
	 *
	 * @param treenode
	 * @param obj
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */

	private void loopField(TreeNode<String> treenode, Object obj) throws IllegalArgumentException, IllegalAccessException  {
		if (obj == null)
			return;

		Class<?> c = obj.getClass();

		if (obj instanceof java.util.List<?>) {
			List<?> list = (List<?>)obj;
			for (Object e:list) {
				loopField(treenode, e);
			}
			return;
		}

		Field []fields = c.getDeclaredFields();
		handleFields(fields, treenode, obj);
		
		Class<?> superClass = obj.getClass().getSuperclass();
		if (!superClass.getName().equals("java.lang.Object")) {
			fields = superClass.getDeclaredFields();
			handleFields(fields, treenode, obj);
		}
	}
	
	private void handleFields(Field []fields, TreeNode<String> treenode, Object obj) 
	  throws IllegalArgumentException, IllegalAccessException {
		for (Field field: fields) {
			XStreamAlias fieldAnn = field.getAnnotation(XStreamAlias.class);
			if (fieldAnn != null) {
				if (fieldAnn.value().equals(treenode.getData())) {
					modifyField(field, obj);
				}
			}

			XStreamImplicit fieldAnnx = field.getAnnotation(XStreamImplicit.class);
			if (fieldAnnx != null) {
				if (fieldAnnx.itemFieldName().equals(treenode.getData())) {
					modifyField(field, obj);
				}
			}
		}
	}

	/**
	 * This method is responsible for actually masking the fields. If the object is a primitive type it will be
	 * set to default values. If it is an object it will be set to null or Object default values (e.g. Float = 0.0
	 * String = "#", Long = 0).
	 *
	 * WARNING: Be careful in setting a rule to mask an entire object. If you set a rule to mask a mandatory object
	 *          then this method will set it to null, as the value is null XTREAM will remove it from the parent object
	 *          and as  a result XSD validation will fail.
	 *
	 *
	 * @param field
	 * @param obj
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private void modifyField(Field field, Object obj) throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		Class<?> tClass = field.getType();

		if (obj == null)
			return;

		if (tClass.isPrimitive()){
			if (tClass.equals(Float.TYPE)) {
				field.set(obj, 0.0f);
			} else if (tClass.equals(Integer.TYPE)) {
				field.set(obj, 0);
			} else if (tClass.equals(Double.TYPE)) {
				field.set(obj, 0.0);
			} else if (tClass.equals(Long.TYPE)) {
				field.set(obj, 0);
			} else if (tClass.equals(Boolean.TYPE)) {
				field.set(obj, false);
			}
		} else {
			if (field.get(obj) == null) //skip empty objects
				return;

			OptionalParam optionsField = field.getAnnotation(OptionalParam.class);
			MaskedValue maskedValue = field.getAnnotation(MaskedValue.class);
			if (optionsField != null) {
				field.set(obj, null);
			} else if (maskedValue != null) {
				field.set(obj, maskedValue.value());
			} else if (tClass.equals(String.class)) {
				field.set(obj, "#");
			} else if (tClass.equals(Integer.class)) {
				field.set(obj, 0);
			} else if (tClass.equals(Long.class)) {
				field.set(obj, 0);
			} else if (tClass.equals(Float.class)) {
				field.set(obj, 0.0f);
			} else if (tClass.equals(Double.class)) {
				field.set(obj, 0.0);
			} else if (tClass.equals(Boolean.class)) {
				field.set(obj, false);
			} else if (tClass.equals(java.util.Date.class)) {
				field.set(obj, new Date(MASKED_DATE.getTime()));
			}
			else if (tClass.equals(List.class)) {
				List l = (List)field.get(obj);
				for (Object objl: l) {
					Field []allfields = objl.getClass().getDeclaredFields();
					for (Field f: allfields) {
						modifyField(f, objl);
					}

				}
			} else if (tClass.equals(Class.class)) {
				//Do nothing, skip it
			} else {
				//Mask All field of this object
				maskAll(field.get(obj));
//				Field []allfields = tClass.getDeclaredFields();
//				for (Field f: allfields) {
//					modifyField(f, field);
//				}
			}
		}
	}

	public IAPISecurity getApiSecurity() {
		return apiSecurity;
	}

	public void setApiSecurity(IAPISecurity apiSecurity) {
		this.apiSecurity = apiSecurity;
	}


	private void maskAll(Object parent) throws IllegalArgumentException, IllegalAccessException {
		if (parent != null) {
			Field []allfields = parent.getClass().getDeclaredFields();
			for (Field f: allfields) {
				System.out.println("Masking :" + f.getName());
				modifyField(f, parent);
			}
		}
	}


	/*
	public static void main(String arg[]) throws IllegalArgumentException, IllegalAccessException {
		APIFieldMasker mask = new APIFieldMasker();
//		mask.addRule("providerResults.pageNum");
//		mask.addRule("providerResults.pageSize");
//		mask.addRule("providerResults.results.error");


		ProviderResults aa = new ProviderResults();

		Results rr = Results.getError("xx", "xxx");
		aa.setResults(rr);

		System.out.println("New Value error:" + aa.getResults().getError());

		System.out.println(mask.toString());
		aa.setPageNum(55);
		aa.setPageSize(55);
		System.out.println("Pre Value:" + aa.getPageNum());
		//mask.mask(aa);
		aa.getResults();
		System.out.println("New Value PageNum:" + aa.getPageNum());
		System.out.println("New Value getPageSize:" + aa.getPageSize());
		System.out.println("New Value error:" + aa.getResults().getError());

	} */
}
