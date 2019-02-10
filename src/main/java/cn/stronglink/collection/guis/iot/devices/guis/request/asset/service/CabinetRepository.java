package cn.stronglink.collection.guis.iot.devices.guis.request.asset.service;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.stronglink.collection.guis.iot.devices.guis.request.asset.entity.CabinetEntity;


public interface CabinetRepository extends JpaRepository<CabinetEntity,Long>{
	void deleteByDevAddrCode(String devAddrCode);
}
