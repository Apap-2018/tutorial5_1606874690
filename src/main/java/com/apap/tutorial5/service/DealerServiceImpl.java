package com.apap.tutorial5.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial5.model.DealerModel;
import com.apap.tutorial5.repository.DealerDb;

@Service
@Transactional
public class DealerServiceImpl implements DealerService{
	@Autowired
	private DealerDb dealerDb;
	
	@Override
	public Optional<DealerModel> getDealerDetailById(Long id) {
		return dealerDb.findById(id);
	}
	
	@Override
	public void addDealer(DealerModel dealer) {
		dealerDb.save(dealer);
	}

	@Override
	public void deleteDealer(DealerModel dealer) {
		dealerDb.delete(dealer);
	}
	
	@Override
	public void updateDealer(DealerModel dealer) {
		for (int i = 0; i < dealerDb.findAll().size(); i++) {
			if (dealerDb.findAll().get(i).getId() == (dealer.getId())) {
				
				DealerModel archive = dealerDb.findAll().get(i);
				int idx = dealerDb.findAll().indexOf(archive);
				
				dealerDb.findAll().get(idx).setAlamat(dealer.getAlamat());
				dealerDb.findAll().get(idx).setNoTelp(dealer.getNoTelp());
			}
		}
	}
	
	@Override
	public DealerDb getDealerDb() {
		return dealerDb;
	}
	
	
}

