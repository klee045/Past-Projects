# brute force seq search
def bruteForce(genomeString, queryString):
    lstOfIndex = []

    for i in range(len(genomeString)-len(queryString)+1):
        for j in range(len(queryString)):
            # check if each letters are the same for target and sequence
            # and prevent going out of index
            if queryString[j] != genomeString[i + j]:
                break
            # if reaches the end of targetString, means string occurrence found
            elif j == len(queryString) - 1:
                lstOfIndex += [i]
    return lstOfIndex
