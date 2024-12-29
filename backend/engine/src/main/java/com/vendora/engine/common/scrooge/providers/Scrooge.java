package com.vendora.engine.common.scrooge.providers;

import com.vendora.engine.common.scrooge.Credentials;
import com.vendora.engine.common.session.model.SessionUser;
import com.vendora.engine.modules.user.model.User;

public interface Scrooge<C extends Credentials> {
  void setContext();
  <U extends User> C generateKeys(U u);
  void destroyKeys();
  SessionUser retrieveKeys();
}
