/**
 * 
 */
package com.bluecloud.persistence.dao;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import com.bluecloud.persistence.pojo.BaseBO;

/**
 * @author Hanlu
 *
 * 2012-10-11
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})}) 
public class PaginationInterceptor implements Interceptor {

	private Log log=LogFactory.getLog(PaginationInterceptor.class);
	/**
	 * 
	 */
	public PaginationInterceptor() {
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler)invocation.getTarget();    
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);    
            
        RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");    
        if(rowBounds == null || rowBounds == RowBounds.DEFAULT){    
            return invocation.proceed();    
        }    
            
        DefaultParameterHandler defaultParameterHandler = (DefaultParameterHandler)metaStatementHandler.getValue("delegate.parameterHandler");    
        Object o= defaultParameterHandler.getParameterObject();   
        Object sidx = null;
        Object sord = null;
        if(o!=null){
        	if(o instanceof BaseBO){
        		BaseBO bo=(BaseBO) o;
        		sidx = bo.getSidx();    
        		sord = bo.getSord();    
        	}else if(o instanceof Map){
        		Map parameterMap=(Map) o;
				sidx = parameterMap.get("sidx");    
        		sord = parameterMap.get("sord");    
        	}
        }
            
        String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");    
            
		if(sidx != null && sord != null){    
            originalSql = originalSql + " order by " + sidx + " " + sord;    
        }    
            
        Configuration configuration = (Configuration)metaStatementHandler.getValue("delegate.configuration");    
            
                            Dialect.Type databaseType  = null;    
        try{    
            databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());    
        } catch(Exception e){    
            //ignore    
        }    
        if(databaseType == null){    
            throw new RuntimeException("在Configration.xml中没有定义dialect属性 : " + configuration.getVariables().getProperty("dialect"));    
        }    
        Dialect dialect = null;    
        switch(databaseType){    
            case ORACLE:    
                dialect = new OracleDialect();    
                break;    
            case MYSQL://需要实现MySQL的分页逻辑    
            	dialect=new MySQLDialect();
                break;    
                    
        }    
        metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, rowBounds.getOffset(), rowBounds.getLimit()) );    
        metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET );    
        metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT );    
        if(log.isDebugEnabled()){    
            BoundSql boundSql = statementHandler.getBoundSql();    
            log.debug("生成分页SQL : " + boundSql.getSql());    
        }    
        return invocation.proceed();   
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
	 */
	@Override
	public Object plugin(Object target) {
		 return Plugin.wrap(target, this); 
	}

	/* (non-Javadoc)
	 * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
	 */
	@Override
	public void setProperties(Properties arg0) {

	}

}
