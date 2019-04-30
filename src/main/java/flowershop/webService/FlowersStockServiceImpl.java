package flowershop.webService;

import flowershop.backend.exception.FlowerValidationException;
import flowershop.backend.services.FlowerService;
import flowershop.frontend.dto.Flower;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.List;

@WebService(endpointInterface = "flowershop.webService.FlowersStockService")
@Service
public class FlowersStockServiceImpl implements FlowersStockService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowersStockServiceImpl.class);

    @Autowired
    private FlowerService flowerService;

    @Override
    public void increaseFlowersStockSize (int count) {
        List<Flower> entities = flowerService.getAll();

        for (Flower e : entities) {
            e.setCount(e.getCount() + count);
            try {
                flowerService.update(e);
            } catch (FlowerValidationException ex) {
                LOG.error("Increase flowers stock error", ex);
            }
        }
        LOG.info("Flowers stock increased by {}", count);
    }
}
