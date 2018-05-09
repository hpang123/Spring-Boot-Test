package hpang.spring.boot.flower.service;

import hpang.spring.boot.flower.model.User;

public interface UserClient {
	long getUniqueUserIds();
	User getModifiedUser();

}