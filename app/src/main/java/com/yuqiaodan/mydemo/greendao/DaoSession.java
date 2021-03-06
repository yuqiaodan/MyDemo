package com.yuqiaodan.mydemo.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.yuqiaodan.mydemo.greendao.bean.Idiom;

import com.yuqiaodan.mydemo.greendao.IdiomDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig idiomDaoConfig;

    private final IdiomDao idiomDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        idiomDaoConfig = daoConfigMap.get(IdiomDao.class).clone();
        idiomDaoConfig.initIdentityScope(type);

        idiomDao = new IdiomDao(idiomDaoConfig, this);

        registerDao(Idiom.class, idiomDao);
    }
    
    public void clear() {
        idiomDaoConfig.clearIdentityScope();
    }

    public IdiomDao getIdiomDao() {
        return idiomDao;
    }

}
