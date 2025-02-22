import java.util.ArrayList;
import java.util.Optional;

public class OwnerCollection {
    private Owner[] owners = new Owner[10];
    private int size;
    
    public boolean addOwner(Owner owner) {
        if (owner == null || containsOwner(owner) || containsOwner(owner.getName())) return false;
        if (size == owners.length) resizeArray();
        owners[size++] = owner;
        return true;
    }

    public boolean removeOwner(Owner owner) {
        return removeByPredicate(o -> o.equals(owner));
    }

    public boolean removeOwner(String name) {
        return removeByPredicate(o -> o.getName().equals(name));
    }

    public boolean containsOwner(Owner owner) {
        return findIndex(o -> o.equals(owner)) >= 0;
    }

    public boolean containsOwner(String name) {
        return findIndex(o -> o.getName().equals(name)) >= 0;
    }

    public Optional<Owner> getOwner(String name) {
        int index = findIndex(o -> o.getName().equals(name));
        return index >= 0 ? Optional.of(owners[index]) : Optional.empty();
    }

    public ArrayList<Owner> getOwners() {
        ArrayList<Owner> ownerList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ownerList.add(owners[i]);
        }
        ownerList.sort((o1, o2) -> o1.getName().compareTo(o2.getName())); // Sortera alfabetiskt
        return ownerList;
    }

    private void resizeArray() {
        Owner[] newOwners = new Owner[owners.length * 2];
        System.arraycopy(owners, 0, newOwners, 0, owners.length);
        owners = newOwners;
    }

    private boolean removeByPredicate(java.util.function.Predicate<Owner> predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test(owners[i])) {
                System.arraycopy(owners, i + 1, owners, i, size - i - 1);
                owners[--size] = null;
                return true;
            }
        }
        return false;
    }

    private int findIndex(java.util.function.Predicate<Owner> predicate) {
        for (int i = 0; i < size; i++) {
            if (predicate.test(owners[i])) return i;
        }
        return -1;
    }
}