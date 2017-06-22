package com.torch.interfaces.user.command;

import org.hibernate.validator.constraints.NotBlank;

public class BindPhoneCommand {

    @NotBlank(message = "bindPhone.phone.required")
    private String phone;

    @NotBlank(message = "bindPhone.identifyCode.required")
    private String identifyCode;

    @NotBlank(message = "bindPhone.password.required")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentifyCode() {
        return identifyCode;
    }

    public void setIdentifyCode(String identifyCode) {
        this.identifyCode = identifyCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Builder bindPhoneCommandBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String phone;

        private String identifyCode;

        private String password;

        public Builder withPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder withIdentifyCode(String identifyCode) {
            this.identifyCode = identifyCode;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public BindPhoneCommand build() {
            BindPhoneCommand bindPhoneCommand = new BindPhoneCommand();
            bindPhoneCommand.setIdentifyCode(this.identifyCode);
            bindPhoneCommand.setPassword(this.password);
            bindPhoneCommand.setPhone(this.phone);
            return bindPhoneCommand;
        }
    }

    @Override
    public String toString() {
        return "{\"phone\":" + this.phone + "," + "\"identifyCode\":" + this.identifyCode + "," + "\"password\":\"" + this.password + "\"}";
    }
}
