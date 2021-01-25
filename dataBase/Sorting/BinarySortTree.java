

public class BinarySortTree {
	 static Node root;
	public static void insert(int data){
		root=insert(root,data);
	}
	public static Node insert(Node node,int data){
		if(node==null){
			node=new Node(data);
		}else{
			if(data<=node.data){
				node.left=insert(node.left,data);
			}else{
				node.right=insert(node.right,data);
			}
		}
		return node;
	}
	
  
	public static void printTree(){
		printTree(root);
	}
	public static void printTree(Node node){
		if(node!=null){
			printTree(node.left);
			System.out.print(node.data+" ");
			printTree(node.right);
		}
	}
}
