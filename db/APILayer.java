package db;
import myexception.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class APILayer {

    private Map<Long,CustomerInfo> customerMap=new HashMap<>();
    private Map<Long,Map<Long, AccountInfo>> accountMap=new HashMap<>();
    Cache cache=null;
    public PersistLayer persistLayer=new PersistLayer();
    
    public void checkStatus(AccountInfo pjObj) throws CustomException {

        if(!pjObj.isStatus())
        {
            throw new CustomException("The account is inactive");
        }
    }

    public void addAccount(AccountInfo pjClass,long id,long accNumber) throws CustomException {
        if (customerMap.get(id)==null) {
            throw new  CustomException("create customer id and customer information first");
        }
        Map<Long, AccountInfo> inpMap=accountMap.get(id);
        if(inpMap==null)
        {
            inpMap=new HashMap<Long,AccountInfo>();
            accountMap.put(id, inpMap);
        }
        inpMap.put(accNumber,pjClass);
    }

    public void addCustomer(CustomerInfo pjClass,long id) throws CustomException {

        customerMap.put(id,pjClass);
    }

    public long deposit(long accNumber,long id,long depositeAmount) throws CustomException {

        AccountInfo pjObj = retrieveFromManyAccount(id,accNumber);
        checkStatus(pjObj);
        if(depositeAmount>0)
        {
            long currentBalance = pjObj.getBalance();
            long newBalance=currentBalance+depositeAmount;
            pjObj.setBalance(newBalance);
            return newBalance;
        }
        throw new CustomException("deposit amount can not be negative or zero");
    }

    public String changeStatus(long id,long accountNo,boolean newStatus) throws CustomException {

        AccountInfo pjObj=retrieveFromManyAccount(id,accountNo);
        boolean bool= pjObj.isStatus();
        if(bool != newStatus)
        {
            pjObj.setStatus(newStatus);
            return "status changed";
        }
        if(bool)
        {
            throw new CustomException("account is already active");
        }
        throw new CustomException("account is already inactive");
    }

    public long withdrawal(long accNumber,long id,long withdrawAmount) throws CustomException {

        AccountInfo pjObj=retrieveFromManyAccount(id,accNumber);
        checkStatus(pjObj);
        long currentBalance=pjObj.getBalance();
        if(withdrawAmount>currentBalance)
        {
            throw new CustomException("insufficient balance");
        }
        long newBalance=currentBalance-withdrawAmount;
        pjObj.setBalance(newBalance);
        return newBalance;
    }

    public long checkBalance(long id,long accountNo) throws CustomException
    {
        AccountInfo pjObj=retrieveFromManyAccount(id,accountNo);
        checkStatus(pjObj);
        return pjObj.getBalance();
    }

    public Map<Long, AccountInfo> retrieveAccount(long id) throws CustomException {

        Map<Long, AccountInfo> tempMap=new HashMap<>();
        tempMap=accountMap.get(id);
        if(tempMap!=null)
        {
            return tempMap;
        }
        throw new CustomException("Customer id not found");
    }

    public CustomerInfo retrieveCustomer(long id) throws CustomException {

        CustomerInfo custObj=new CustomerInfo();
        custObj=customerMap.get(id);
        if(custObj!=null)
        {
            return custObj;
        }
        throw new CustomException("Customer id not found");
    }

    public AccountInfo retrieveFromManyAccount(long id,long accNumber) throws CustomException {

        Map<Long, AccountInfo> tempMap=new HashMap<>();
        tempMap=accountMap.get(id);
        AccountInfo temp=new AccountInfo();
        if(tempMap!=null)
        {
            temp=tempMap.get(accNumber);
            if(temp!=null)
            {
                return temp;
            }
        }
        throw new CustomException("Customer id and account number not found");
    }

    public void writeFile() throws Exception {
       cache=persistLayer.mapToFile(accountMap,customerMap);
       accountMap=cache.accountMap;
       customerMap=cache.customerMap;
    }

    public void readFile() throws ClassNotFoundException, IOException {
      
        cache=persistLayer.readFromFile();
        accountMap=cache.accountMap;
        customerMap=cache.customerMap;
        
    }
}