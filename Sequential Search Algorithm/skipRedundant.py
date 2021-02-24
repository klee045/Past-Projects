def skipRedundant(genomeString, queryString):
    i = 0  # initial index of genomeString
    j = 0  # initial index of queryString
    firstRepeatedPos = -1
    repeatedFound = False
    occurrences = []

    while i < (len(genomeString) - len(queryString) + 1):
        if queryString[j] != genomeString[i]:
            i += 1  # move to next letter in genome

        # if first letters are equal
        else:
            # The loop will run all the way to the end,
            # then decide how many indexes to skip based on if
            # there is occurrence of first char of qString.
            # OCCURRENCE FOUND -> SKIP TO THAT INDEX
            # NO OCCURRENCE FOUND -> SKIP BY M
            while j < len(queryString)-1:
                j += 1

                # check for appearance of first character of queryString in substring of genomeString
                if (queryString[0] == genomeString[i+j] and not(repeatedFound)):
                    firstRepeatedPos = j
                    repeatedFound = True

                # if char not equal
                # set j to -1 to account for end of loop & last query char not
                # matching since both will exit loop
                if queryString[j] != genomeString[i + j]:
                    j = -1
                    break

            # reach end of queryString
            if j == len(queryString)-1:
                occurrences += [i]

            # skip to 1st occurrence of queryString[0]
            if repeatedFound:
                i += firstRepeatedPos  # firstRepeatedPos = j
                firstRepeatedPos = -1  # Reset for next iteration
                repeatedFound = False  # Reset for next iteration
            else:
                i += 1

            j = 0  # reset j = 0 if j was kept as occ was found

    return occurrences
