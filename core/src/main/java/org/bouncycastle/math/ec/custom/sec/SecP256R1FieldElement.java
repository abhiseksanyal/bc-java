package org.bouncycastle.math.ec.custom.sec;

import java.math.BigInteger;

import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.util.Arrays;

public class SecP256R1FieldElement extends ECFieldElement
{
    public static final BigInteger Q = SecP256R1Curve.q;
    public static final BigInteger Qr = SecP256R1Curve.r;

    protected int[] x;

    public SecP256R1FieldElement(BigInteger x)
    {
        if (x == null || x.signum() < 0 || x.compareTo(Q) >= 0)
        {
            throw new IllegalArgumentException("x value invalid in FEp256k1 field element");
        }

        this.x = SecP256R1Field.fromBigInteger(x);
    }

    protected SecP256R1FieldElement(int[] x)
    {
        this.x = x;
    }

    public boolean isZero()
    {
        return SecP256R1Field.isZero(x);
    }

    public boolean isOne()
    {
        return SecP256R1Field.isOne(x);
    }

    public boolean testBitZero()
    {
        return SecP256R1Field.testBit(x, 0);
    }

    public BigInteger toBigInteger()
    {
        return SecP256R1Field.toBigInteger(x);
    }

    public String getFieldName()
    {
        return "SecP256R1Field";
    }

    public int getFieldSize()
    {
        return Q.bitLength();
    }

    public BigInteger getQ()
    {
        return Q;
    }

    public ECFieldElement add(ECFieldElement b)
    {
        int[] z = SecP256R1Field.create();
        SecP256R1Field.add(x, ((SecP256R1FieldElement)b).x, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement addOne()
    {
        int[] z = SecP256R1Field.create();
        SecP256R1Field.addOne(x, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement subtract(ECFieldElement b)
    {
        int[] z = SecP256R1Field.create();
        SecP256R1Field.subtract(x, ((SecP256R1FieldElement)b).x, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement multiply(ECFieldElement b)
    {
        int[] z = SecP256R1Field.create();
        SecP256R1Field.multiply(x, ((SecP256R1FieldElement)b).x, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement divide(ECFieldElement b)
    {
        int[] y = SecP256R1Field.fromBigInteger(b.invert().toBigInteger());
        int[] z = SecP256R1Field.create();
        SecP256R1Field.multiply(x, y, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement negate()
    {
        int[] z = SecP256R1Field.create();
        SecP256R1Field.negate(x, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement square()
    {
        int[] z = SecP256R1Field.create();
        SecP256R1Field.square(x, z);
        return new SecP256R1FieldElement(z);
    }

    public ECFieldElement invert()
    {
        return new SecP256R1FieldElement(toBigInteger().modInverse(Q));
    }

    // D.1.4 91
    /**
     * return a sqrt root - the routine verifies that the calculation returns the right value - if
     * none exists it returns null.
     */
    public ECFieldElement sqrt()
    {
        ECFieldElement root = new ECFieldElement.Fp(Q, toBigInteger()).sqrt();
        return root == null ? null : new SecP256R1FieldElement(root.toBigInteger());
    }

    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }

        if (!(other instanceof SecP256R1FieldElement))
        {
            return false;
        }

        SecP256R1FieldElement o = (SecP256R1FieldElement)other;
        return Arrays.areEqual(x, o.x);
    }

    public int hashCode()
    {
        return Q.hashCode() ^ Arrays.hashCode(x);
    }
}
