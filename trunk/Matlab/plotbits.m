function plotbits( bits, bitsPerRow )
%PLOTBITS Summary of this function goes here
%   Detailed explanation goes here
len = length(bits);
rows = ceil(len/bitsPerRow);
pad = zeros(1,rows*bitsPerRow-len);
bits = [bits pad];
arr = [];
for i=0:rows-1
    arr = [arr; bits(i*bitsPerRow+1:(i+1)*bitsPerRow)];
end
imshow(arr);
