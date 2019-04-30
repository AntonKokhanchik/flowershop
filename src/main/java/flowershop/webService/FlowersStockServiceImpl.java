package flowershop.webService;

import flowershop.backend.dao.FlowerDAO;
import flowershop.backend.entity.FlowerEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "flowershop.webService.FlowersStockService")
@Service
public class FlowersStockServiceImpl implements FlowersStockService {
    @Autowired
    private FlowerDAO flowerDAO;

    @Override
    public void increaseFlowersStockSize (int count) {
        List<FlowerEntity> entities = flowerDAO.getAll();

        for (FlowerEntity e : entities) {
            e.setCount(e.getCount() + count);
            flowerDAO.update(e);
        }
        LoggerFactory.getLogger(FlowersStockServiceImpl.class).info("Flowers stock increased by {}", count);
    }
}
