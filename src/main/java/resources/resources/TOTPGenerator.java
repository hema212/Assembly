package resources;

import org.jboss.aerogear.security.otp.Totp;

public class TOTPGenerator {
	 /**
     * Method is used to get the TOTP based on the security token
     * @return
     */
    public static String getTwoFactorCode(){

    	//Replace with your security key copied from step 12
        Totp totp = new Totp("zo4sdheptxczc3jac6arh2n352yj6ksn"); // 2FA secret key
        String twoFactorCode = totp.now(); //Generated 2FA code here
        return twoFactorCode;
    }
}
