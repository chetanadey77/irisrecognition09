function histplotall( )
%HISTPLOTALL Summary of this function goes here
%   Detailed explanation goes here
histplot('results_best_single_match.txt','results_best_single_non_match.txt', 'Gabor, one eye')
figure
histplot('results_best_gauss_match.txt','results_best_gauss_non_match.txt', 'Gaussian, one eye')
figure
histplot('results_best_double_match.txt','results_best_double_non_match.txt', 'Gabor, two eyes')

