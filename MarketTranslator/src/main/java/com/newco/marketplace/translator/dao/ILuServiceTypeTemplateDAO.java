package com.newco.marketplace.translator.dao;

public interface ILuServiceTypeTemplateDAO {
    
    /**
     * Default finder; finds by service_type_template_id
     * @param id
     * @return LuServiceTypeTemplate
     */
    public LuServiceTypeTemplate findById(Integer id);
    
    /**
     * Finds by given primary skill node id and description
     * @param nodeId
     * @param descr
     * @return LuServiceTypeTemplate
     */
    public LuServiceTypeTemplate findByTemplateIdAndNodeId(final Integer nodeId, final String descr);
}