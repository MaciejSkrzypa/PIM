package pl.edu.pwr.abis.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Odpowiedz endpointu ekspertow IPMA")
public class EkspertIPMAResponseDTO {

    @Schema(description = "Status przetwarzania", example = "OK")
    private String status;

    @Schema(description = "Komunikat odpowiedzi", example = "Eksperci IPMA zostali zapisani")
    private String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ArraySchema(schema = @Schema(implementation = EkspertIPMADTO.class))
    private List<EkspertIPMADTO> eksperci = new ArrayList<>();

    public EkspertIPMAResponseDTO() {}

    public EkspertIPMAResponseDTO(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public EkspertIPMAResponseDTO(String status, String message, List<EkspertIPMADTO> eksperci) {
        this.status = status;
        this.message = message;
        this.eksperci = eksperci != null ? eksperci : new ArrayList<>();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<EkspertIPMADTO> getEksperci() {
        return eksperci;
    }

    public void setEksperci(List<EkspertIPMADTO> eksperci) {
        this.eksperci = eksperci != null ? eksperci : new ArrayList<>();
    }
}
