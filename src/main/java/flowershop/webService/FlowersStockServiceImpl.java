package flowershop.webService;

import flowershop.backend.entity.FlowerEntity;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@WebService(endpointInterface = "flowershop.webService.FlowersStockService")
@Service
public class FlowersStockServiceImpl implements FlowersStockService {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void increaseFlowersStockSize (int count){
        List<FlowerEntity> entities = em.createNamedQuery("getAllFlowers", FlowerEntity.class).getResultList();

        for(FlowerEntity e : entities){
            e.setCount(e.getCount() + count);
        }
        LoggerFactory.getLogger(FlowersStockServiceImpl.class).info("Flowers stock increased by " + count);
    }
}
