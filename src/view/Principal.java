package view;

import java.util.concurrent.Semaphore;

import controller.TransacaoCaixa;


public class Principal
{

	public static void main(String[] args)
	{
		int permissoes = 1;
		Semaphore semaforo1 = new Semaphore(permissoes);
		Semaphore semaforo2 = new Semaphore(permissoes);
		
		for (int idTransacao = 1; idTransacao < 21; idTransacao++)
		{
			Thread t = new TransacaoCaixa(idTransacao, semaforo1, semaforo2);
			t.start();
		}

	}

}