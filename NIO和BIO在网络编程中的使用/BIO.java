public class BIOServer{
	public static  void main(String[] args){
		ServerSocket serverSocket = new ServerSocket(9988);
		ServerSocket socket = serverSocket.accept();
		var is = socket.getInputStream();
		var os = socket.getOutputStream();//获取到两个流Stream这样就可以进行ServerSocket和Socket之间的数据沟通了

		socket.close();
		is.close();
		os.close();
	}
}

public class BIOClient{
	public static  void main(String[] args){
		//或者使用new Socket(InetAdderss inetAddress , int port);
		Socket socket = new Socket("127.0.0.1" , 9988);
		socket.getInputStream();
		socket.getOutputStream();
		//对流进行操作，就可以跟服务器之间进行交互了
		//关闭资源
	}
}