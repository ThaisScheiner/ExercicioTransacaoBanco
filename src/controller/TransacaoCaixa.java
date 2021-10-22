package controller;

import java.util.concurrent.Semaphore;

public class TransacaoCaixa extends Thread
{
	private Semaphore semaforo1;
	private Semaphore semaforo2;
	private int idTransacao, tipo;
	private double valor;
	private static double saldo = 1000;
	private static int contador;

	public TransacaoCaixa(int idTransacao, Semaphore semaforo1, Semaphore semaforo2)
	{
		this.idTransacao = idTransacao;
		this.semaforo1 = semaforo1;
		this.semaforo2 = semaforo2;
	}

	@Override
	public void run()
	{
		tipoTransacao();
		Transacao();

		if(contador == 19)
		{
			dormir(1000);
			System.out.printf("\n     Saldo final: R$ %.2f\n", saldo);
		}
	}

	private void tipoTransacao()
	{
		tipo = (int) ((Math.random() * 101) + 1);
		valor = ((Math.random() * 201) + 10);
	}

	private boolean ePar(int tipo)
	{
		if(tipo % 2 == 0)
		{
			return true;
		}
		return false;
	}

	private void Transacao()
	{
		if(ePar(tipo))
		{
			try
			{
				semaforo1.acquire();
				System.err.println("#" + idTransacao + ": sacando valor da conta...");
				saldo -= valor;
				dormir(3000);
				System.err.printf("#%d: R$ %.2f sacados da conta.\n", idTransacao, valor);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			finally
			{
				semaforo1.release();
			}
		}
		else
		{
			try
			{
				semaforo2.acquire();
				System.out.println("#" + idTransacao + ": depositando valor na conta...");
				saldo += valor;
				dormir(3000);
				System.out.printf("#%d: R$ %.2f depositados na conta.\n", idTransacao, valor);
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			finally
			{
				semaforo2.release();
			}
		}
		contador ++;
	}

	private void dormir(int tempo) {
		try
		{
			sleep(tempo);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}