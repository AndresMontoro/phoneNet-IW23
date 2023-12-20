package es.uca.iw.views.misConsumos;

import java.util.List;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import es.uca.iw.model.Contract;
import es.uca.iw.services.ContractService;
import es.uca.iw.views.MainLayout;
import jakarta.annotation.security.PermitAll;

@Route(value = "MisConsumos", layout = MainLayout.class)
@PermitAll
public class MisConsumosView extends VerticalLayout {
    private ContractService contractService;
    
    public MisConsumosView(ContractService contractService) {
        this.contractService = contractService;

        List<Contract> userContracts = contractService.getContracts();
        for (Contract contract : userContracts) {
            try {
                contractService.getContractDataConsumption(contract);
            } catch (Exception e) {
                Notification.show("Datos del contrato desactualizados: " + e.getMessage());
            }
        }
    }
}
