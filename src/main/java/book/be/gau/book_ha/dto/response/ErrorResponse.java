package book.be.gau.book_ha.dto.response;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
  private String message;

  private Integer status;

  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private ZonedDateTime create_date;

  private Object data;
}
