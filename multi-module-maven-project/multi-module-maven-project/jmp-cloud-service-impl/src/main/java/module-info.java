module jmp.cloud.service.impl {
	uses com.example.cloud.service.impl.dao.UserDao;
	uses com.example.cloud.service.impl.dao.SubscriptionDao;

	requires transitive jmp.service.api;
	requires jmp.dto;

	exports com.example.cloud.service.impl;

	provides com.example.service.api.Service with com.example.cloud.service.impl.CloudServiceImpl;
}