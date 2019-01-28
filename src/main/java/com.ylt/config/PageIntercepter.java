package com.ylt.config;

import com.ylt.common.Page;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageIntercepter implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        //通过MetaObject元数据取得方法名id：com.XXX.queryMessageListByPage
        String id = mappedStatement.getId();
        //匹配在mybatis中定义的与分页有关的查询id
        if (id.matches(".+ByPage$")) {
            //BoundSql中有原始的sql语句和对应的查询参数
            BoundSql boundSql = statementHandler.getBoundSql();
            Object params = boundSql.getParameterObject();
            Page page = null;
            if (params instanceof Page) {
                page = (Page) params;
            } else if (params instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) params;
                Object page1 = map.get("param1");
                if (page1 instanceof Page) {
                    page = (Page) page1;
                }
            }
            if (page == null) {
                return invocation.proceed();
            }

            String sql = boundSql.getSql();
            String countSql = "select count(*)from (" + sql + ")a";
            Connection connection = (Connection) invocation.getArgs()[0];
            PreparedStatement countStatement = connection.prepareStatement(countSql);
            ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
            parameterHandler.setParameters(countStatement);
            ResultSet rs = countStatement.executeQuery();
            if (rs.next()) {
                //为什么是getInt（1）? 因为数据表的列是从1开始计数
                page.setAllCount(rs.getInt(1));
                double ceil = Math.ceil(page.getAllCount() * 1.0 / page.getPageSize());
                page.setAllPage((int) ceil);
            }
            String pageSql = sql + " limit " + page.getCurrentPage() * page.getPageSize() + "," + (page.getCurrentPage() + 1) * page.getPageSize();
            metaObject.setValue("delegate.boundSql.sql", pageSql);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
