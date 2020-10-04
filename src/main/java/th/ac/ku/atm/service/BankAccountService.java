package th.ac.ku.atm.service;

//import org.mindrot.jbcrypt.BCrypt;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.atm.model.BankAccount;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BankAccountService {

    private RestTemplate restTemplate;

    private List<BankAccount> bankAccountList;

    public BankAccountService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<BankAccount> getCustomerBankAccount(int customerId) {
        String url = "http://localhost:8091/api/bankaccount/customer/" +
                customerId;
        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();

        return Arrays.asList(accounts);
    }



    @PostConstruct
    public void postConstruct() {
        this.bankAccountList = new ArrayList<>();
    }

//        public void createBankAccount(BankAccount bankAccount) {
////         hash pin which I don't know how to do it
//        String hashPin = hash(bankAccount.getId());
//        bankAccount.setId(hashPin);
//        bankAccountList.add(bankAccount);
//    }

    public void createBankAccount(BankAccount bankAccount) {
//         hash pin which I don't know how to do it
        String Id = bankAccount.getId();
        bankAccount.setId(Id);
        bankAccountList.add(bankAccount);
    }

//    public void createBankAccount(BankAccount bankAccount) {
////         hash pin which I don't know how to do it
//        String Id = bankAccount.getId();
//        bankAccount.setId(Id);
//        int CustomerId = bankAccount.getCustomerId();
//        bankAccount.setCustomerId(CustomerId);
//        String Type = bankAccount.getType();
//        bankAccount.setType(Type);
//        double Balance = bankAccount.getBalance();
//        bankAccount.setBalance(Balance);
////        bankAccount.add(bankAccount);
//    }


    public List<BankAccount> getBankAccounts() {
        String url = "http://localhost:8091/api/bankaccount/";

        ResponseEntity<BankAccount[]> response =
                restTemplate.getForEntity(url, BankAccount[].class);

        BankAccount[] accounts = response.getBody();
        return Arrays.asList(accounts);

    }

    public BankAccount findBankAccount(String id) {
        for (BankAccount bankAccount : bankAccountList) {
            if (bankAccount.getId() == id)
                return bankAccount;
        }
        return null;
    }

//        public BankAccount checkId(BankAccount inputCustomerId) {
//        // 1. หา customer ที่มี id ตรงกับพารามิเตอร์
//        BankAccount storedBankAccount = findBankAccount(inputCustomerId.getId());
//
//        // 2. ถ้ามี id ตรง ให้เช็ค pin ว่าตรงกันไหม โดยใช้ฟังก์ชันเกี่ยวกับ hash
//        if (storedBankAccount != null) {
//            String hashPin = storedBankAccount.getId();
//
//            if (BCrypt.checkpw(inputCustomerId.getId(), hashPin))
//                return storedBankAccount;
//        }
//        // 3. ถ้าไม่ตรง ต้องคืนค่า null
//        return null;
//    }

    public BankAccount checkId(BankAccount inputCustomerId) {
        // 1. หา customer ที่มี id ตรงกับพารามิเตอร์
        BankAccount storedBankAccount = findBankAccount(inputCustomerId.getId());

        // 2. ถ้ามี id ตรง ให้เช็ค pin ว่าตรงกันไหม โดยใช้ฟังก์ชันเกี่ยวกับ hash
        if (storedBankAccount != null) {
            String Id = storedBankAccount.getId();

            if (inputCustomerId.getId() == Id)
                return storedBankAccount;
        }
        // 3. ถ้าไม่ตรง ต้องคืนค่า null
        return null;
    }


    private String hash(String pin) {
        String salt = BCrypt.gensalt(12);
        return BCrypt.hashpw(pin, salt);
    }

    public void openAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount";

        restTemplate.postForObject(url, bankAccount, BankAccount.class);
    }

    public BankAccount getBankAccount(int id) {
        String url = "http://localhost:8091/api/bankaccount/" + id;

        ResponseEntity<BankAccount> response =
                restTemplate.getForEntity(url, BankAccount.class);

        return response.getBody();
    }

    public void editBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId();
        restTemplate.put(url, bankAccount);
    }

    public void deleteBankAccount(BankAccount bankAccount) {
        String url = "http://localhost:8091/api/bankaccount/" +
                bankAccount.getId();
        restTemplate.put(url, bankAccount);
    }


}

