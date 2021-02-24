import time
from bruteForce import bruteForce
from compareSUM1 import compareSUM1
from skipRedundant import skipRedundant


# open FNA file and get lines of genome as list
def getGenomeString(fileName):
    with open(fileName) as f:
        genome = f.readlines()
        del genome[0]  # remove extra data

    genomeString = ""
    for line in genome:
        genomeString += line[:-1]  # remove \n and combine into single string
    return genomeString


def averageOfN(averageN, fn, arg1, arg2):
    lst = []
    for i in range(averageN + 1):
        start_time = time.time()
        fn(arg1, arg2)
        lst += [time.time() - start_time]
    return sum(lst) / len(lst)


if __name__ == "__main__":
    # change name of file and query sequence here
    genomeString = getGenomeString("GCF_000006945.2_ASM694v2_genomic.fna")
    queryString = "CGCCTGTT"
    # genomeString = "AABAABAABAABAABAABAAB"
    # queryString = "AABAA"

    n = 20  # set number of runs to avg

    # run search
    bruteForce_result = bruteForce(genomeString, queryString)
    print("bruteForce_result =", bruteForce_result)
    skipRedundant_result = skipRedundant(genomeString, queryString)
    print("skipRedundant_result =", skipRedundant_result)
    compareSUM1_result = compareSUM1(genomeString, queryString)
    print("compareSUM1_result =", compareSUM1_result)

    # length of list
    print("len(bruteForce): ", len(bruteForce_result))
    print("len(skipRedundant): ", len(skipRedundant_result))
    print("len(compareSUM1): ", len(compareSUM1_result))

    print("bruteForce == skipRedundant:",
          bruteForce_result == skipRedundant_result)
    print("bruteForce == compareSUM1:",
          bruteForce_result == compareSUM1_result)

    # print("Average Times:")
    # print(
    #     "Brute Force Ao" + str(n) + ":",
    #     averageOfN(n, bruteForce, genomeString, queryString),
    # )

    # print(
    #     "compareSUM1 Ao" + str(n) + ":",
    #     averageOfN(n, compareSUM1, genomeString, queryString),
    # )

    # print(
    #     "skipRedundant Ao" + str(n) + ":",
    #     averageOfN(n, skipRedundant, genomeString, queryString),
    # )
