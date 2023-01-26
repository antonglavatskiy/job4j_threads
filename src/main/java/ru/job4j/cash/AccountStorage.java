package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.put(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        Optional<Account> from = getById(fromId);
        Optional<Account> to = getById(toId);
        if (from.isPresent() && to.isPresent()
                && from.get().amount() >= amount) {
            Account fromAcc = new Account(from.get().id(), from.get().amount() - amount);
            Account toAcc = new Account(to.get().id(), to.get().amount() + amount);
            update(fromAcc);
            update(toAcc);
            rsl = true;
        }
        return rsl;
    }
}
