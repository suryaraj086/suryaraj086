package db;

import java.io.IOException;
import java.util.Map;

import myexception.CustomException;

public interface Storage {
	
	public Cache mapToFile(Map<Long,Map<Long, AccountInfo>> accMap,Map<Long, CustomerInfo> cusMap) throws CustomException, IOException, Exception;
	public Cache readFromFile() throws IOException, ClassNotFoundException;
    public Cache updateAccountTable(long id,String name,long balance,String branch,boolean status);
    public Cache updateCustomerTable(long id,String name,int age,char gender);
}
