package gmail.dankim0124.datacallingdemo.model.pk;

import lombok.Data;

import java.io.Serializable;

@Data
public class SocketTickPK implements Serializable {
    private Long sequentialId;
    private String code;
}
