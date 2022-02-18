package db;

import java.io.*;
import java.util.Map;
import myexception.CustomException;

public class PersistLayer implements Storage  {

    public long accNo=0;
    public long idNo=0;
	
  	
  	
    public Cache mapToFile(Map<Long,Map<Long, AccountInfo>> inpAccountMap,Map<Long, CustomerInfo> customerMap) throws CustomException, IOException {
    	Cache cache=new Cache();
        if(!inpAccountMap.isEmpty())
        {
            File file=new File("accountmap.txt");
            try( FileOutputStream fos = new FileOutputStream(file,false);
                 ObjectOutputStream oos = new ObjectOutputStream(fos);)
            {
                oos.writeObject(inpAccountMap);
                oos.writeObject(accNo);
                oos.writeObject(idNo);
                oos.writeObject(customerMap);
                cache.storeInCache(inpAccountMap,accNo,idNo,customerMap);
            }
            return cache;
        }
      throw new CustomException("Map is empty");
    }

    public Cache readFromFile() throws IOException, ClassNotFoundException {
    	Cache cache=new Cache();
        try( FileInputStream fis = new FileInputStream("accountmap.txt");
             ObjectInputStream ois = new ObjectInputStream(fis);)
        {
           cache.accountMap = (Map<Long, Map<Long, AccountInfo>>) ois.readObject();
           accNo =  (long) ois.readObject();
           cache.accNo= accNo;
           idNo = (long) ois.readObject();
           cache.idNo=idNo;
           cache.customerMap = (Map<Long, CustomerInfo>) ois.readObject();
        }
        return cache;
    }

	@Override
	public Cache updateAccountTable(long id, String name, long balance, String branch, boolean status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cache updateCustomerTable(long id, String name, int age, char gender) {
		// TODO Auto-generated method stub
		return null;
	}
}