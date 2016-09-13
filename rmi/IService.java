package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author  liyc
 * @date 2016年9月13日 下午2:21:15
*/
public interface IService extends Remote {  
		public String queryName(String no) throws RemoteException; 
}
