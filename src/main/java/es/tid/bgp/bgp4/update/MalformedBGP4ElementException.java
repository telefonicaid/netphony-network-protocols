package es.tid.bgp.bgp4.update;

public class MalformedBGP4ElementException extends RuntimeException
{

	private final static long serialVersionUID = 1L;

	public MalformedBGP4ElementException(){ super(); }

	public MalformedBGP4ElementException(String message)
	{
		super(message);
	}
}
