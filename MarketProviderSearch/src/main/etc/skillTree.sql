SELECT 
       CONCAT('S', @row := @row + 1) AS id,
       sk.node_id                             AS NodeId,
       REPLACE(TRIM(sk.node_name), '  ', ' ') AS NodeName,
       sk.parent_node                         AS ParentId,
       sk.level                               AS Levell,
       REPLACE(TRIM(tk.node_name), '  ', ' ') AS ParentName, 
        NULL  AS SkuDesc,   
       "skilltree"                            AS doctype
FROM   skill_tree AS sk
       LEFT OUTER JOIN skill_tree AS tk
       ON sk.parent_node = tk.node_id
       , (SELECT @row := 0) r

     UNION
       SELECT 
       CONCAT('S', @row1 := @row1 + 1) AS id,
       sk.node_id                             AS NodeId,
       REPLACE(TRIM(sk.node_name), '  ', ' ') AS NodeName,
       sk.parent_node                         AS ParentId,
       sk.level                               AS Levell,
       REPLACE(TRIM(tk.node_name), '  ', ' ') AS ParentName, 
        REPLACE(TRIM(bs.sku_description), '  ', ' ') AS SkuDesc,      
       "skilltree"                            AS doctype
FROM   buyer_sku bs
       JOIN buyer_sku_task bst
         ON bs.sku_id = bst.sku_id AND bs.buyer_id = 3333
       JOIN skill_tree sk
         ON sk.node_id = bst.category_node_id
       LEFT OUTER JOIN skill_tree AS tk
                    ON sk.parent_node = tk.node_id
                    ,(SELECT @row1 := 10000) r1