package com.vendora.engine.security.cerberus;

import com.vendora.engine.modules.user.model.User;

public interface Cerberus<C, I extends Cerberus<C, I>> {
  I context();
  <U extends User> C generateKeys(U u);
  void destroyKeys();
  C retrieveKeys();
}
