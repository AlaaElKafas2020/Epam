module application {
	uses com.example.bank.api.Bank;
	uses com.example.service.api.Service;

	requires jmp.cloud.bank.impl;
	requires jmp.cloud.service.impl;
	requires java.logging;
	requires jmp.dto;
}