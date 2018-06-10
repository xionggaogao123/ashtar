package com.small.call.business.service;

import com.ashstr.common.domain.Paging;
import com.small.call.api.criteria.UserCriteria;
import com.small.call.api.domain.User;
import com.small.call.api.service.UserService;
import com.small.call.business.dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author keven
 * @date 2018-06-10 下午5:37
 * @Description
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Long create(Object object) {
        return (long)userDao.insert((User) object);
    }

    @Override
    public User findById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public List<User> findByIds(List ids) {
        return userDao.selectByIds(ids);
    }

    @Override
    public Paging paging(UserCriteria criteria) {
        return null;
    }

    @Override
    public boolean update(Object object) {
        return userDao.update((User) object) > 0;
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id) > 0;
    }

}
