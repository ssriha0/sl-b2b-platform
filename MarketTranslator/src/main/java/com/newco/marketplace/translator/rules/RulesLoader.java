package com.newco.marketplace.translator.rules;

import java.io.InputStreamReader;
import java.io.Reader;

import org.drools.RuleBase;
import org.drools.RuleBaseFactory;
import org.drools.compiler.PackageBuilder;
import org.drools.rule.Package;

public class RulesLoader {

	private static RuleBase ruleBase = null;
	
   /**
     * Please note that this is the "low level" rule assembly API.
     */
	private static void loadRules() throws Exception {
		//read in the source
		Reader source = new InputStreamReader( RulesLoader.class.getResourceAsStream( "\\rules\\PricingRules.dslr" ) );
		
		//optionally read in the DSL (if you are using it).
		//Reader dsl = new InputStreamReader( RulesLoader.class.getResourceAsStream( "\\rules\\PricingLanguage.dsl" ) );

		//Use package builder to build up a rule package.
		//An alternative lower level class called "DrlParser" can also be used...
		
		PackageBuilder builder = new PackageBuilder();

		//this wil parse and compile in one step
		//NOTE: There are 2 methods here, the one argument one is for normal DRL.
		builder.addPackageFromDrl( source );

		//add ruleflow
		//source = new InputStreamReader( RulesLoader.class.getResourceAsStream( "/rules/PricingFlow.rf" ) );
		//builder.addRuleFlow(source);
		
		//get the compiled package (which is serializable)
		Package pkg = builder.getPackage();
		
		//add the package to a rulebase (deploy the rule package).
		RuleBase myRuleBase = RuleBaseFactory.newRuleBase();
		myRuleBase.addPackage( pkg );
		
		ruleBase = myRuleBase;
	}

	public static RuleBase getRuleBase() throws Exception {
		if (ruleBase == null) {
			loadRules();
		}
		return ruleBase;
	}
    
}
