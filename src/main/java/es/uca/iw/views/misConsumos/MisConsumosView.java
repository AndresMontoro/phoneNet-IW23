package es.uca.iw.views.misConsumos;

import java.util.List;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
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

        List<Contract> actualUserContracts = contractService.getContracts();

        for (Contract contract : actualUserContracts) {
            add(new H2("Tarifa: " + contract.getProduct().getName()));
            add(new H3("Consumo de datos: " + this.contractService.getDataConsumption(contract, this.contractService.getFirstDayOfMonth()) + " MB de " 
                + contract.getProduct().getDataUsageLimit() + " MB"));
            add(new H3("Consumo de llamadas: "));
        }
    }
}
