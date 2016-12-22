package com.redisqueue;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by alan on 15/9/11.
 */
public class LogUtil {

    private static final String BIZPARAM = "bizParam";

    /**
     * 设置日志中的业务参数
     *
     * @param bizId
     */
    public static void setBizId(String... bizId) {
        String bizParam = MDC.get(BIZPARAM);
        if (StringUtils.isBlank(bizParam)) {
            bizParam = Joiner.on(",").skipNulls().join(bizId);
            MDC.put(BIZPARAM, bizParam);
        } else {
            StringBuilder sbd = new StringBuilder()
                    .append(bizParam)
                    .append(",").append(Joiner.on(",").skipNulls().join(bizId));

            MDC.put(BIZPARAM, sbd.toString());
        }
    }

    /**
     * 日志打点,加入清除原有数据的代码
     *
     * @param bizId
     */
    public static void setBizIdWithClear(String... bizId) {
        MDC.clear();
        setBizId(bizId);
    }

    //------------------------------------
    static RedisTemplate redisTemplate;

    public static RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public static void setRedisTemplate(RedisTemplate redisTemplate) {
        LogUtil.redisTemplate = redisTemplate;
    }

    public void set(String key,Object value){
        //ValueOperations 理解成Map<Object,Object>


//        redisTemplate.opsForValue().set("redis-key","I'm test spring-data-redis");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key,value);


        //BoundValueOperations的理解对保存的值做一些细微的操作
//        BoundValueOperations boundValueOperations = redisTemplate.boundValueOps(key);
    }
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
    public void setList(String key ,List<?> value){
        //ListOperations可以理解为List<Object>
        ListOperations listOperations= redisTemplate.opsForList();
        listOperations.leftPush(key, value);
//                .leftPushAll(value);
    }
    public Object getList(String key){
        //ListOperations可以理解为List<Object>
        return redisTemplate.opsForList().leftPop("test-list");
    }
    public void setSet(String key ,Set<?> value){
        SetOperations setOperations= redisTemplate.opsForSet();
        setOperations.add(key, value);
    }
    public Object getSet(String key){
        return redisTemplate.opsForSet().members(key);
    }


    public void setHash(String key ,Map<String,?> value){
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.putAll(key,value);
    }
    public Object getHash(String key){
        return redisTemplate.opsForHash().entries(key);
    }


    public void delete(String key){
        redisTemplate.delete(key);
    }
//    public void clearAll(){
//        redisTemplate.multi();
//    }

    /**
     * 发布频道消息
     * @param channel
     * @param message
     * @return 返回订阅者数量
     */
    public Long publish(final String channel,final String message) {
// TODO Auto-generated method stub
        Long recvs = (Long)this.getRedisTemplate().execute(new RedisCallback<Object>() {


            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
// TODO Auto-generated method stub
                return connection.publish(channel.getBytes(), message.getBytes());
            }
        });
        return recvs;
    }

    //入队key 是消息频道 ，value 消息内容

    public Long putToQueue(final String key, final String value) {

        Long l = (Long) this.getRedisTemplate().execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
// TODO Auto-generated method stub
                return connection.lPush(key.getBytes(), value.getBytes());
            }
        });
        return l;
    }

    //读取消息 （读过队列中消息就没有了）key 是消息频道
    public String getFromQueue(final String key) {
// TODO Auto-generated method stub
        byte[] b = (byte[]) this.getRedisTemplate().execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
// TODO Auto-generated method stub
                return connection.rPop(key.getBytes());
            }
        });
        if(b != null)
        {
            return new String(b);
        }
        return null;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-redis.xml");
        redisTemplate = (RedisTemplate)ctx.getBean("redisTemplate");
        LogUtil jedis = new LogUtil();
        //queue

        /*Long a=jedis.publish("aa","zhangsan");
        System.out.println(a);*/

        String c=jedis.getFromQueue("aa");
        System.out.println(c);

        /*Long b=jedis.putToQueue("aa", "lisi");
        System.out.println(b);
        Long d=jedis.putToQueue("aa", "zhangsan");
        System.out.println(d);*/





        //String
       /* jedis.set("test-string", "good-中国抗战胜利");
        System.out.println("test-string = " + jedis.get("test-string"));*/
        //POJO
        /*User user =new User();
        user.setName("邓洋");
        user.setBirthday(new Date());
        user.setSex(true);
        jedis.set("test-user", user);
        System.out.println("test-user = " + jedis.get("test-user"));
        System.out.println("test-user:name = " + ((User)jedis.get("test-user")).getName());*/
        //List
        /*List<String> list = new ArrayList<String>();
        list.add("张三");
        list.add("李四");
        list.add("麻子");
        String key_list = "test-list";
        jedis.setList(key_list, list);


        List<String> test_list = (List<String>)jedis.getList(key_list);
        System.out.println(test_list);
        for (int i = 0; i < test_list.size(); i++) {
            System.out.println(i + " = " + test_list.get(i));
        }
        jedis.delete(key_list);
        System.out.println((List<String>)jedis.getList(key_list));*/

        //list-other

        //Map
        /*String key_map = "test-map";
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("map1","map-张三");
        map.put("map2","map-李四");
        map.put("map3", "map-麻子");
        jedis.setHash(key_map, map);
        Map<String,Object> getMap = (Map<String,Object>)jedis.getHash(key_map);
        for(Map.Entry<String,Object> a:getMap.entrySet()){

            System.out.println("key="+a.getKey()+":value="+a.getValue());
        }
        return;*/
    }

}
