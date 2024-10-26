package nl.garagemeijer.salesapi.helpers;


import nl.garagemeijer.salesapi.enums.Status;
import nl.garagemeijer.salesapi.models.Purchase;
import nl.garagemeijer.salesapi.models.Sale;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CheckAndChangeStatus {

    public Status returnNewSaleStatus(Sale sale) {
        if (sale.getStatus() == Status.NEW) {
            return Status.PENDING;
        } else if (sale.getTypeOrder().contains("order") && sale.getCustomer() != null) {
            return Status.CLOSED;
        } else {
            return Status.OPEN;
        }
    }

    public Status returnNewPurchaseStatus(Purchase purchase) {
        if (purchase.getExpectedDeliveryDate().isBefore(LocalDate.now())) {
            return Status.CLOSED;
        } else {
            return Status.PENDING;
        }
    }
}
