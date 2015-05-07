sharewifi.info [![Build Status](https://api.shippable.com/projects/551732065ab6cc1352ae3af8/badge?branchName=master)](https://app.shippable.com/projects/551732065ab6cc1352ae3af8/builds/latest)
===============

# Naming convention in Java code ([Ref](http://source.android.com/source/code-style.html#follow-field-naming-conventions))

- View field names start with v.
- Non-public, non-static field names start with m.
- Static field names start with s.
- Other fields start with a lower case letter.
- Public static final fields (constants) are ALL_CAPS_WITH_UNDERSCORES.

For example:

```
public class MyClass {
    public static final int SOME_CONSTANT = 42;
    public int publicField;
    private static MyClass sSingleton;
    int mPackagePrivate;
    private int mPrivate;
    protected int mProtected;
    
    @InjectView(R.id.container)
    DrawerLayout vDrawerLayout;
}
```

# Other rules

- Should not define `static final` in `Fragment` / `Activity`. Use `Constants.java` instead.
- `static final` can be defined in `Model` / `Service`.