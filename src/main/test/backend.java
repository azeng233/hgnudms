import cn.zengchen233.dao.user.UserMapper;
import cn.zengchen233.pojo.User;
import cn.zengchen233.service.user.UserService;
import cn.zengchen233.service.user.UserServiceImpl;
import cn.zengchen233.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class backend {
    @Test
    public void login() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        User admin = mapper.getLoginUser("zengchen");
        System.out.println(admin);

        sqlSession.close();
    }
}
