package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "verification_codes")
public class VerificationCode {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column(nullable = false)
        private String phoneNumber;

        @Column(nullable = false)
        private String code;

        @Column(nullable = false)
        private LocalDateTime expirationTime;

        @Column(name = "phone_confirmed_at")
        private LocalDateTime phoneConfirmedAt;

        @ManyToOne
        @JoinColumn(name = "user_id")
        @JsonBackReference
        private User user;

        public LocalDateTime getPhoneConfirmedAt() {
                return phoneConfirmedAt;
        }

        public void setPhoneConfirmedAt(LocalDateTime phoneConfirmedAt) {
                this.phoneConfirmedAt = phoneConfirmedAt;
        }

}
