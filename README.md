with vw_tbl as (
	SELECT upper(tbl.relname) AS tab_name
	    ,concat(lower(split_part(tbl.relname, '_', 1))
	           ,initcap(lower(split_part(tbl.relname, '_', 2)))
	           ,initcap(lower(split_part(tbl.relname, '_', 3)))
	           ,initcap(lower(split_part(tbl.relname, '_', 4)))
	           ,initcap(lower(split_part(tbl.relname, '_', 5)))
	           ,initcap(lower(split_part(tbl.relname, '_', 6)))) 			AS tab_camel
	    ,concat(substring(lower(split_part(tbl.relname, '_', 1)), 1, 1)
	           ,substring(lower(split_part(tbl.relname, '_', 2)), 1, 1)
	           ,substring(lower(split_part(tbl.relname, '_', 3)), 1, 1)
	           ,substring(lower(split_part(tbl.relname, '_', 4)), 1, 1)
	           ,substring(lower(split_part(tbl.relname, '_', 5)), 1, 1)
	           ,substring(lower(split_part(tbl.relname, '_', 6)), 1, 1)) 	AS tab_alias
	    ,tab_desc.description 												AS tab_desc
	    ,col.ordinal_position 												AS col_no
	    ,upper(col.column_name) 											AS col_name
	    ,concat(lower(split_part(col.column_name, '_', 1))
	    	   ,initcap(lower(split_part(col.column_name, '_', 2)))
	    	   ,initcap(lower(split_part(col.column_name, '_', 3)))
	    	   ,initcap(lower(split_part(col.column_name, '_', 4)))
	    	   ,initcap(lower(split_part(col.column_name, '_', 5)))
	    	   ,initcap(lower(split_part(col.column_name, '_', 6)))) 		AS col_camel
	    ,col_desc.description 												AS col_desc
	    ,concat(replace(col.udt_name, 'bpchar', 'char')
		       ,CASE
		            WHEN col.character_maximum_length IS NOT NULL THEN ('(' || col.character_maximum_length) || ')'
		            WHEN col.numeric_precision IS NOT NULL THEN ('(' || col.numeric_precision) ||
		            CASE
		                WHEN col.numeric_scale::integer = 0 THEN ')'
		                ELSE (',' || col.numeric_scale) || ')'
		            END
		            ELSE NULL
		        end
		 ) 																	AS col_type
	    ,CASE
	         WHEN col.is_nullable = 'NO' THEN 'Not Null'
	         ELSE ''
	     END 																AS col_isnull
	    ,CASE
	         WHEN POSITION((':') IN (col.column_default)) > 0 THEN substr(col.column_default, 1, POSITION((':') IN (col.column_default)) - 1) 
	         ELSE col.column_default 
	     END 																AS col_default
	    ,col_pk.ordinal_position 											AS col_pk
	    ,replace(col.udt_name, 'bpchar', 'char')							as col_type1
		,CASE
	            WHEN col.character_maximum_length IS NOT NULL THEN ('' || col.character_maximum_length)
	            WHEN col.numeric_precision IS NOT NULL THEN ('' || col.numeric_precision) ||
		            CASE
		                WHEN col.numeric_scale::integer = 0 THEN ''
		                ELSE (',' || col.numeric_scale) || ''
		            END
	            ELSE NULL
		 end																as col_len
	   FROM pg_stat_user_tables tbl
	     LEFT JOIN pg_description tab_desc ON tbl.relid = tab_desc.objoid AND tab_desc.objsubid = 0
	     JOIN information_schema.columns col ON tbl.relname = col.table_name::name
	     LEFT JOIN pg_description col_desc ON col_desc.objoid = tbl.relid AND col_desc.objsubid = col.ordinal_position::integer
	     LEFT JOIN information_schema.key_column_usage col_pk ON (col_pk.constraint_name::name = (col.table_name || '_pkey') OR col_pk.constraint_name::name = (col.table_name || '_pk')) AND col_pk.column_name::name = col.column_name::name
	  WHERE 1 = 1 
--	  AND tbl.relname like 'rt_%' or tbl.relname = 'totalbldg_kt_jisa'
	  AND tbl.relname like 'tb_%' or tbl.relname like 'tmp_%'
)
	select 'select '''||tab_name||''', count(*) from mespown.'||tab_name||' union all' from vw_tbl group by tab_name;
select 
    * 
-- distinct lower(tab_name)
--'COMMENT ON COLUMN '||tab_name||'.'||col_name||' IS '''||col_desc||''';'
  from vw_tbl
where 1=1 
--	and tab_name like upper('%'||'rt_'||'%')		-- 테이블 이름
--	and tab_name like upper('%'||'mdm_cpnt'||'%')		-- 테이블 이름
--	and tab_desc like '%'||''||'%'						-- 테이블 Comment
--	and col_name like upper('%'||'jisa'||'') 			-- 칼럼 이름
--	and col_desc like '%'||'지사'||'%'					-- 칼럼 Comment
--  and (col_type1 = 'date' or col_type1 = 'timestamp')	-- 칼럼 Type 
--	and col_default != ''								-- 칼럼 Default
--	and tab_name not like 'RT%'
  order by tab_name, col_no
;
