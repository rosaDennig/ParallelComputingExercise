package philoProblem;

class Table {
    private boolean[] m_bForkUsed;

    public Table(int numPhilosophers) {
        m_bForkUsed = new boolean[numPhilosophers];
        for (int i = 0; i < numPhilosophers; i++) {
            m_bForkUsed[i] = false;
        }
    }

    public synchronized void TakeForks(int nPosition) throws InterruptedException {
        int leftFork = nPosition;
        int rightFork = (nPosition + 1) % m_bForkUsed.length;

        while (m_bForkUsed[leftFork] || m_bForkUsed[rightFork]) {
            wait();
        }

        m_bForkUsed[leftFork] = true;
        m_bForkUsed[rightFork] = true;
    }

    public synchronized void ReturnForks(int nPosition) {
        int leftFork = nPosition;
        int rightFork = (nPosition + 1) % m_bForkUsed.length;

        m_bForkUsed[leftFork] = false;
        m_bForkUsed[rightFork] = false;
        notifyAll();
        System.out.println("Philosopher " + nPosition + " sent forks free.");
    }
}
