package com.codeit.actuator.health;

import com.codeit.actuator.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentApiHealthIndicator implements HealthIndicator {

  private final PaymentService paymentService;

  @Override
  public Health health() {

    try {
      //결제 API 상태 확인
      boolean isAvailable = paymentService.checkHealth();

      if(isAvailable) {
        return Health.up()
            .withDetail("api", "Payment API")
            .withDetail("status", "Available")
            .withDetail("message", "결제 API가 정상 동작 중입니다.")
            .build();
      } else {
        return Health.down()
            .withDetail("api", "Payment API")
            .withDetail("status", "Unavailable")
            .withDetail("message", "결제 API를 사용할 수 없습니다.")
            .build();
      }
    } catch (Exception e) {
      return Health.down()
          .withDetail("api", "Payment API")
          .withDetail("error", "e.getMessage()")
          .withException(e) // 예외 정보 포함
          .withDetail("message", "결제 API 상태 확인 불가!")
          .build();
      }
  }
}
