package com.vendora.engine.security.cerberus;

import com.vendora.engine.common.session.model.SessionUser;
import com.vendora.engine.modules.user.model.User;
import com.vendora.engine.security.cerberus.credentials.Credentials;

public interface Cerberus<C extends Credentials> {
  void setContext();
  <U extends User> C generateKeys(U u);
  void destroyKeys();
  SessionUser retrieveKeys();
}
