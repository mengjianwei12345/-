













public class SM2 {
	
	// 测试参数
	public static final String[] ecParams = {
		"8542D69E4C044F18E8B92435BF6FF7DE457283915C45517D722EDB8B08F1DFC3",
		"787968B4FA32C3FD2417842E73BBFEFF2F3C848B6831D7E0EC65228B3937E498",
		"63E4C6D3B23B0C849CF84241484BFE48F61D59A5B16BA06E6E12D1DA27C5249A",
		"8542D69E4C044F18E8B92435BF6FF7DD297720630485628D5AE74EE7C32E79B7",
		"421DEBD61B62EAB6746434EBC3CC315E32220B3BADD50BDC4C4E6C147FEDD43D",
		"0680512BCBB42C07D47349D2153B70C4E5D7FDFCBFA36EA1A85841B9E46E09A2"
	};

//	// 正式参数
//	public static String[] ecParams = {
//		"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF",
//		"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC",
//		"28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93",
//		"FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123",
//		"32C4AE2C1F1981195F9904466A39C9948FE30BBFF2660BE1715A4589334C74C7",
//		"BC3736A2F4F6779C59BDCEE36B692153D0A9877CC62A474002DF32E52139F0A0"
//	};

	public static SM2 instance() {
		return new SM2();
	}

	public final BigInteger ecP;
	public final BigInteger ecA;
	public final BigInteger ecB;
	public final BigInteger ecN;
	public final BigInteger ecGx;
	public final BigInteger ecGy;
	public final ECCurve ecCurve;
	public final ECPoint ecPoint;
	public final ECDomainParameters ecDomainParam;
	public final ECKeyPairGenerator ecKeyPairGen;

	public SM2() {
		this.ecP = new BigInteger(ecParams[0], 16);
		this.ecA = new BigInteger(ecParams[1], 16);
		this.ecB = new BigInteger(ecParams[2], 16);
		this.ecN = new BigInteger(ecParams[3], 16);
		this.ecGx = new BigInteger(ecParams[4], 16);
		this.ecGy = new BigInteger(ecParams[5], 16);

		this.ecCurve = new ECCurve.Fp(this.ecP, this.ecA, this.ecB);
		this.ecPoint = this.ecCurve.createPoint(ecGx, ecGy);
		this.ecDomainParam = new ECDomainParameters(this.ecCurve, this.ecPoint, this.ecN);

		ECKeyGenerationParameters eckgp = new ECKeyGenerationParameters(this.ecDomainParam, new SecureRandom());
		this.ecKeyPairGen = new ECKeyPairGenerator();
		this.ecKeyPairGen.init(eckgp);
	}

	public byte[] sm2GetZ(byte[] userId, ECPoint userKey) {
		SM3Digest sm3 = new SM3Digest();

		int len = userId.length * 8;
		sm3.update((byte) (len >> 8 & 0xFF));
		sm3.update((byte) (len & 0xFF));
		sm3.update(userId, 0, userId.length);

		byte[] p = Codec.bigIntToBytes(ecA);
		sm3.update(p, 0, p.length);

		p = Codec.bigIntToBytes(ecB);
		sm3.update(p, 0, p.length);

		p = Codec.bigIntToBytes(ecGx);
		sm3.update(p, 0, p.length);

		p = Codec.bigIntToBytes(ecGy);
		sm3.update(p, 0, p.length);

		p = Codec.bigIntToBytes(userKey.normalize().getXCoord().toBigInteger());
		sm3.update(p, 0, p.length);

		p = Codec.bigIntToBytes(userKey.normalize().getYCoord().toBigInteger());
		sm3.update(p, 0, p.length);

		byte[] md = new byte[sm3.getDigestSize()];
		sm3.doFinal(md, 0);
		return md;
	}

	public void sm2Sign(byte[] md, BigInteger userD, ECPoint userKey, SM2Result sm2Result) {
		BigInteger e = new BigInteger(1, md);
		BigInteger k = null;
		ECPoint kp = null;
		BigInteger r = null;
		BigInteger s = null;
		do {
			do {
				// 正式环境
				AsymmetricCipherKeyPair keypair = ecKeyPairGen.generateKeyPair();
				ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) keypair.getPrivate();
				ECPublicKeyParameters ecpub = (ECPublicKeyParameters) keypair.getPublic();
				k = ecpriv.getD();
				kp = ecpub.getQ();

				// 国密规范测试 随机数k
				String kS = "6CB28D99385C175C94F94E934817663FC176D925DD72B727260DBAAE1FB2F96F";
				k = new BigInteger(kS, 16);
				kp = this.ecPoint.multiply(k);

				// r
				r = e.add(kp.normalize().getXCoord().toBigInteger());
				r = r.mod(ecN);
			} while (r.equals(BigInteger.ZERO) || r.add(k).equals(ecN));

			// (1 + dA)~-1
			BigInteger da_1 = userD.add(BigInteger.ONE);
			da_1 = da_1.modInverse(ecN);

			// s
			s = r.multiply(userD);
			s = k.subtract(s).mod(ecN);
			s = da_1.multiply(s).mod(ecN);
		} while (s.equals(BigInteger.ZERO));

		sm2Result.r = r;
		sm2Result.s = s;
	}

	public void sm2Verify(byte md[], ECPoint userKey, BigInteger r, BigInteger s, SM2Result result) {
		result.R = null;
		BigInteger e = new BigInteger(1, md);
		BigInteger t = r.add(s).mod(ecN);
		if (!t.equals(BigInteger.ZERO)) {
			ECPoint ecp = ecPoint.multiply(result.s).add(userKey.multiply(t));
			result.R = e.add(ecp.normalize().getXCoord().toBigInteger()).mod(ecN);
		}
	}
}
