package flowershop.webService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface FlowersStockService {
    @WebMethod
    void increaseFlowersStockSize ( @WebParam(name = "count")int count );
}
