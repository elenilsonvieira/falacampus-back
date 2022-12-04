package br.edu.ifpb.dac.falacampus.exceptions;

public class CommentSolvedException extends Exception {
	
	public CommentSolvedException(){
		 super(String.format("Comment solved, cannot be deleted!"));
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
