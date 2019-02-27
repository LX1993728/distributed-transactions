package com.jpa.starter.repository;

import com.jpa.starter.utils.BeanCopyer;
import com.jpa.starter.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class GeneralService {
    private Logger logger = LoggerFactory.getLogger(GeneralService.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * @param page
     * @param pageSize
     * @param parent
     * @param target
     * @param params
     * @return
     * @apiNote 封装底层查询 用于继承的实体查询（单表策略）
     */
    public PageVO criteriaQuery(Long page, Long pageSize, Class<?> parent, Class<?> target, Map<String, Object> params,String descTimeField) {
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        Long index = (page - 1) * pageSize;
        StringBuffer resultSqlBuffer = new StringBuffer();
        StringBuffer countSqlBuffer = new StringBuffer();
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        countSqlBuffer.append("SELECT count(*) FROM " + parentName + " a " + (targetName != null ? (" WHERE type(a)=" + targetName) : "") + token);
        resultSqlBuffer.append("SELECT a FROM " + parentName + " a " + (targetName != null ? ("  WHERE a.class=" + targetName) : "") + token);

        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                String sqlMiddle = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                resultSqlBuffer.append(sqlMiddle);
                countSqlBuffer.append(sqlMiddle);
            }
        }

        if (descTimeField != null){
            resultSqlBuffer.append(" ORDER BY a."+descTimeField +" DESC");
        }
        logger.info("resultSql= {}", resultSqlBuffer.toString());
        logger.info("countSql= {}", countSqlBuffer.toString());
        final long totalSize = em.createQuery(countSqlBuffer.toString(), Long.class).getSingleResult().longValue();
        Long totalPage = (totalSize + pageSize - 1) / pageSize;
        logger.info("总条数: {}", totalSize);
        final List<?> resultList = em.createQuery(resultSqlBuffer.toString(), parent)
                .setFirstResult(index.intValue()).setMaxResults(pageSize.intValue())
                .getResultList();
        PageVO pageVO = new PageVO(resultList, totalPage, page, pageSize);
        return pageVO;
    }

    /**
     * @param parent
     * @param target
     * @return
     * @apiNote 获取父类子类的总条数
     */
    public Long getTotalCount(Class<?> parent, Class<?> target, Map<String, Object> params) {
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        String preSql = "SELECT count(*) FROM " + parentName + " a " + (targetName != null ? (" WHERE type(a)=" + targetName) : "") + token;
        StringBuffer sqlBuffer = new StringBuffer(preSql);
        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                String sqlMiddle = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                sqlBuffer.append(sqlMiddle);
            }
        }

        final long count = em.createQuery(sqlBuffer.toString(), Long.class).getSingleResult().longValue();
        return count;
    }

    /**
     * @param parent
     * @param target
     * @param params
     * @param beginDate
     * @param endDate
     * @param timeField
     * @return
     * @apiNote 流量查询
     */
    public Long getCountBetweenDate(Class<?> parent, Class<?> target, Map<String, Object> params, Date beginDate, Date endDate, String timeField) {
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        String preSql = "SELECT count(*) FROM " + parentName + " a " + (targetName != null ? ("  WHERE a.class=" + targetName) : "") + token;
        StringBuffer sqlBuffer = new StringBuffer(preSql);
        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                String sqlMiddle = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                sqlBuffer.append(sqlMiddle);
            }
        }
        if (beginDate != null && endDate != null && timeField != null) {
            String sqlMiddle = " AND a." + timeField + ">=:beginDate AND a." + timeField + "<:endDate";
            sqlBuffer.append(sqlMiddle);

        }
        final TypedQuery<Long> query = em.createQuery(sqlBuffer.toString(), Long.class);
        if (beginDate != null && endDate != null && timeField != null) {
            query.setParameter("beginDate", beginDate);
            query.setParameter("endDate", endDate);
        }
        final long count = query.getSingleResult().longValue();

        return count;
    }

    /**
     * @param parent
     * @param target
     * @param field
     * @param fieldValue
     * @apiNote 根据指定String类型字段插入或更新数据
     */
    @Transactional
    public void saveOrUpdateByField(Class<?> parent, Class<?> target, String field, String fieldValue, Object object){
        final String parentName = parent.getSimpleName();
        final String targetName = target.getSimpleName();
        String querySQL = "SELECT a FROM " + parentName + " a WHERE type(a)=" + targetName + " AND a." + field + "='" + fieldValue + "'";
        logger.info("==== saveOrUpdate sql=  {} ", querySQL);
        final List<?> resultList = em.createQuery(querySQL, parent).getResultList();
        try {
            for (int i = 0; i < resultList.size(); i++) {
//            em.remove(resultList.get(i));
                BeanCopyer.copy(object,resultList.get(i));
                em.merge(resultList.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        if (resultList.size() == 0){
            em.persist(object);
        }
    }

    @Transactional
    public void persisent(Object object) {
        em.persist(object);
    }

    @Transactional
    public void merge(Object object) {
        em.merge(object);
    }

    public Object findById(Class<?> parent, Long id) {
        final Object o = em.find(parent, id);
        return o;
    }

   @Transactional
    public void  delete(Object object) throws Exception{
        em.remove(object);
   }

    /**
     * @apiNote 根据条件删除对象(通常是删除外键关联的对象)
     * @param parent
     * @param target
     * @param params
     * @throws Exception
     */
    @Transactional
    @Modifying
    public void delete( Class<?> parent, Class<?> target, Map<String, Object> params) throws Exception{
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        StringBuffer resultSqlBuffer = new StringBuffer();
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        resultSqlBuffer.append("DELETE FROM " + parentName + " a " + (targetName != null ? ("  WHERE a.class=" + targetName) : "") + token);

        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                String sqlMiddle;
                if(entry.getValue() != null){
                    final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                    sqlMiddle  = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                }else {
                    sqlMiddle = "a." + attribute + "  IS NULL " + (entryCount == params.keySet().size() ? "" : " AND ");
                }
                resultSqlBuffer.append(sqlMiddle);
            }
        }
        em.createQuery(resultSqlBuffer.toString()).executeUpdate();
    }

    /**
     *
     * @param parent
     * @param target
     * @param params
     * @return
     * @throws Exception
     */
    public List<?> findByAttribute( Class<?> parent, Class<?> target, Map<String, Object> params) throws Exception{
        String parentName = parent.getSimpleName();
        String targetName = target != null ? target.getSimpleName() : null;
        StringBuffer resultSqlBuffer = new StringBuffer();
        String token = (params == null || params.keySet().size() == 0) ? "" : (targetName == null ? " WHERE " : " AND ");
        resultSqlBuffer.append("SELECT  a FROM " + parentName + " a " + (targetName != null ? ("  WHERE a.class=" + targetName) : "") + token);

        if (params != null) {
            int entryCount = 0;
            for (Entry<String, Object> entry : params.entrySet()) {
                entryCount++;
                final String attribute = entry.getKey();
                String sqlMiddle;
                if(entry.getValue() != null){
                    final String value = (entry.getValue() instanceof String) ? "'" + entry.getValue() + "'" : entry.getValue().toString();
                    sqlMiddle  = "a." + attribute + "=" + value + " " + (entryCount == params.keySet().size() ? "" : " AND ");
                }else {
                    sqlMiddle = "a." + attribute + "  IS NULL " + (entryCount == params.keySet().size() ? "" : " AND ");
                }
                resultSqlBuffer.append(sqlMiddle);
            }
        }
        final List<?> resultList = em.createQuery(resultSqlBuffer.toString(), parent).getResultList();
        return resultList;
    }


    /**
     * @apiNote 更新部分字段
     * @param object 只赋值部分属性的对象
     * @param clazz 对象的类型
     * @param id 对象的id
     * @return
     */
    @Transactional
    public Object updateFieldsById(Object object,Class<?> clazz, Long id) throws Exception{
        Object old = em.find(clazz, id);
        Object result = em.merge(old);
        BeanCopyer.copy(object,old);
        return result;
    }

}
